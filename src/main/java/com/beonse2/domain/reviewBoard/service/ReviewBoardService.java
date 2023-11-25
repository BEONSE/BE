package com.beonse2.domain.reviewBoard.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.branch.mapper.BranchMapper;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.mapper.CouponMapper;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import com.beonse2.domain.reviewBoard.dto.ReviewBoardDTO;
import com.beonse2.domain.reviewBoard.mapper.ReviewBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewBoardService {

    private final ReviewBoardMapper reviewBoardMapper;
    private final MemberMapper memberMapper;
    private final CouponMapper couponMapper;
    private final BranchMapper branchMapper;
    private final JwtUtil jwtUtil;

    public SuccessMessageDTO createReviewBoard(Long couponId,
                                               MultipartFile image,
                                               ReviewBoardDTO reviewBoardDTO,
                                               String accessToken) throws IOException {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        CouponResponseDTO couponResponseDTO = couponMapper.findById(couponId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COUPON)
        );

        if (couponResponseDTO.getBranchName() == null) {
            throw new CustomException(DONT_WRITE_REVIEW);
        }

        if (couponResponseDTO.isUsed()) {
            throw new CustomException(ALREADY_WRITTEN_REVIEW);
        }
        Long branchId = branchMapper.findByBranchName(couponResponseDTO.getBranchName());

        if (image != null && !image.isEmpty()) {
            reviewBoardDTO = ReviewBoardDTO.builder()
                    .memberMid(findMember.getMid())
                    .branchBid(branchId)
                    .couponCid(couponResponseDTO.getCid())
                    .title(reviewBoardDTO.getTitle())
                    .content(reviewBoardDTO.getContent())
                    .writer(findMember.getNickname())
                    .image(reviewBoardDTO.getImage())
                    .reviewOriginalFileName(image.getOriginalFilename())
                    .reviewImageType(image.getContentType())
                    .reviewImageData(image.getBytes())
                    .build();
        } else {
            reviewBoardDTO = ReviewBoardDTO.builder()
                    .memberMid(findMember.getMid())
                    .branchBid(branchId)
                    .couponCid(couponResponseDTO.getCid())
                    .title(reviewBoardDTO.getTitle())
                    .content(reviewBoardDTO.getContent())
                    .writer(findMember.getNickname())
                    .build();
        }

        reviewBoardMapper.createReviewBoard(reviewBoardDTO);

        couponResponseDTO.updateStatus(true);

        couponMapper.updateCouponWriteReview(couponResponseDTO);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("리뷰 작성")
                .build();
    }

    public PageResponseDTO findReviewBoardPage(int page, Long branchId) {

        int totalRows = reviewBoardMapper.getCount(branchId);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .paramId(branchId)
                .rowsPerPage(20)
                .pagesPerGroup(5)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<ReviewBoardDTO> reviewBoardDTOS = reviewBoardMapper.reviewBoardPage(pageRequestDTO);

        for (ReviewBoardDTO reviewBoardDTO : reviewBoardDTOS) {
            reviewBoardDTO.updateGrade(reviewBoardDTO.getPaymentAmount() < 150000 ? 3 : reviewBoardDTO.getPaymentAmount() < 300000 ? 2 : 1);
        }

        if (reviewBoardDTOS.isEmpty()) {
            throw new CustomException(NOT_FOUND_BOARD);
        }

        return PageResponseDTO.builder()
                .content(reviewBoardDTOS)
                .page(page)
                .size(5)
                .totalRows(totalRows)
                .totalPageNo(pageRequestDTO.getTotalPageNo())
                .build();
    }

    public SuccessMessageDTO updateReviewBoard(Long rbId,
                                               MultipartFile image,
                                               ReviewBoardDTO updatedReviewBoardDTO,
                                               String accessToken) throws IOException {
        String token = jwtUtil.resolveToken(accessToken);
        String email = jwtUtil.getEmail(token);

        // 리뷰 게시판이 사용자에게 속하는지 확인
        ReviewBoardDTO reviewBoardDTO = reviewBoardMapper.findByReviewBoardId(rbId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        if (!email.equals(reviewBoardDTO.getWriter())) {
            throw new CustomException(NOT_MATCH_USER);
        }

        if (image != null && !image.isEmpty()) {
            reviewBoardDTO = ReviewBoardDTO.builder()
                    .title(updatedReviewBoardDTO.getTitle())
                    .content(updatedReviewBoardDTO.getContent())
                    .reviewOriginalFileName(image.getOriginalFilename())
                    .reviewImageType(image.getContentType())
                    .reviewImageData(image.getBytes())
                    .build();
        } else {
            reviewBoardDTO = ReviewBoardDTO.builder()
                    .title(updatedReviewBoardDTO.getTitle())
                    .content(updatedReviewBoardDTO.getContent())
                    .build();
        }
        // update 메서드를 호출하여 수정된 내용을 DB에 반영
        reviewBoardMapper.updateReviewBoard(reviewBoardDTO);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("게시글 수정 성공.")
                .build();
    }

    public SuccessMessageDTO removeReviewBoard(Long rbId, String accessToken) {

        String token = jwtUtil.resolveToken(accessToken);
        String email = jwtUtil.getEmail(token);
        System.out.println("getEmail(token)" + email);

        // 리뷰 게시판이 사용자에게 속하는지 확인
        ReviewBoardDTO reviewBoardDTO = reviewBoardMapper.findByReviewBoardId(rbId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        if (!email.equals(reviewBoardDTO.getWriter())) {
            throw new CustomException(NOT_MATCH_USER);
        }

        reviewBoardMapper.deleteReviewBoard(rbId);
        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("게시글 삭제 완료")
                .build();
    }
}

/*org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"undefined\"\r\n\tat org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver.resolveArgument(AbstractNamedValueMethodArgumentResolver.java:133)\r\n\tat org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:122)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:179)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:146)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)\r\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1072)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:965)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:529)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:623)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:209)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat com.beonse2.config.jwt.JwtAuthenticationFilter.doFilter(JwtAuthenticationFilter.java:77)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:337)\r\n\tat org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:115)\r\n\tat org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:81)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:122)\r\n\tat org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:116)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:126)\r\n\tat org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:81)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:109)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:149)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat com.beonse2.config.jwt.JwtAuthenticationFilter.doFilter(JwtAuthenticationFilter.java:77)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat com.beonse2.config.jwt.JwtAuthenticationFilter.doFilter(JwtAuthenticationFilter.java:77)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:103)\r\n\tat org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:89)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90)\r\n\tat org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:112)\r\n\tat org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:82)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:55)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.security.web.session.DisableEncodeUrlFilter.doFilterInternal(DisableEncodeUrlFilter.java:42)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346)\r\n\tat org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:221)\r\n\tat org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:186)\r\n\tat org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:354)\r\n\tat org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:267)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:168)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:481)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:130)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:390)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:928)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1794)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n\tat java.base/java.lang.Thread.run(Thread.java:829)\r\nCaused by: java.lang.NumberFormatException: For input string: \"undefined\"\r\n\tat java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)\r\n\tat java.base/java.lang.Long.parseLong(Long.java:692)\r\n\tat java.base/java.lang.Long.valueOf(Long.java:1144)\r\n\tat org.springframework.util.NumberUtils.parseNumber(NumberUtils.java:214)\r\n\tat org.springframework.beans.propertyeditors.CustomNumberEditor.setAsText(CustomNumberEditor.java:115)\r\n\tat org.springframework.beans.TypeConverterDelegate.doConvertTextValue(TypeConverterDelegate.java:429)\r\n\tat org.springframework.beans.TypeConverterDelegate.doConvertValue(TypeConverterDelegate.java:402)\r\n\tat org.springframework.beans.TypeConverterDelegate.convertIfNecessary(TypeConverterDelegate.java:155)\r\n\tat org.springframework.beans.TypeConverterSupport.convertIfNecessary(TypeConverterSupport.java:73)\r\n\tat org.springframework.beans.TypeConverterSupport.convertIfNecessary(TypeConverterSupport.java:53)\r\n\tat org.springframework.validation.DataBinder.convertIfNecessary(DataBinder.java:729)\r\n\tat org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver.resolveArgument(AbstractNamedValueMethodArgumentResolver.java:125)\r\n\t... 95 more\r\n"*/