package com.beonse2.config.service;

import com.beonse2.config.response.BaseResponse;
import com.beonse2.config.response.SingleDataResponse;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    public <T> SingleDataResponse<T> getSingleDataResponse(boolean success, String message, T data) {
        SingleDataResponse<T> response = new SingleDataResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);

        return response;
    }

    public BaseResponse getBaseResponse(boolean success, String message) {
        BaseResponse response = new BaseResponse();
        response.setSuccess(success);
        response.setMessage(message);

        return response;
    }
}
