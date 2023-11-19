package com.beonse2.domain.myPage.mapper;

import com.beonse2.domain.myPage.vo.MyPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MyPageMapper {

    Optional<MyPage> findById(Long mid);

    void updateEdit(MyPage myPage);

}
