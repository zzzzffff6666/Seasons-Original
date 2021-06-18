package com.zhang.seasonsgateway.model;

import java.util.HashMap;
import java.util.Map;

public class AjaxResult {
    private static final long serialVersionUID = -121291381821L;
    private static final String CODE_TAG = "code";
    private static final String RESULT_TAG = "result";
    private static final String SUCCESS_MSG_TAG = "success";
    private static final String ERROR_MSG_TAG = "error";
    private static final String SUCCESS_MSG = "success";
    private static final String ERROR_MSG = "error";

    public static Map<String, Object> success() {
        return success(SUCCESS_MSG);
    }

    public static Map<String, Object> success(String msg) {
        Map<String, Object> ajaxResult = new HashMap<>();
        ajaxResult.put(CODE_TAG, APICode.SUCCESS);
        ajaxResult.put(SUCCESS_MSG_TAG, msg);
        return ajaxResult;
    }

    public static Map<String, Object> success(String msg, Object result) {
        Map<String, Object> ajaxResult = new HashMap<>();
        ajaxResult.put(CODE_TAG, APICode.SUCCESS);
        ajaxResult.put(SUCCESS_MSG_TAG, msg);
        ajaxResult.put(RESULT_TAG, result);
        return ajaxResult;
    }

    public static Map<String, Object> error(int code) {
        return error(code, ERROR_MSG);
    }

    public static Map<String, Object> error(int code, String msg) {
        Map<String, Object> ajaxResult = new HashMap<>();
        ajaxResult.put(CODE_TAG, code);
        ajaxResult.put(ERROR_MSG_TAG, msg);
        return ajaxResult;
    }
}
