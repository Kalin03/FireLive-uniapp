import Message from '../utils/Message'
import { LOCAL_STORAGE_KEY } from "@/utils/Constants.js";
import Api from "@/utils/Api.js"

const contentTypeForm = 'application/x-www-form-urlencoded;charset=UTF-8'
const contentTypeJson = 'application/json'
const responseTypeJson = 'json'


let port = uni.getSystemInfoSync().uniPlatform;
let BASE_URL = Api.domain + "/api";
if (port === "web") {
    BASE_URL = "/api";
} else {
    BASE_URL = Api.domain + "/api"
}

export const get = (url, params) => {
    // 发起 GET 请求逻辑
    return request({
        url,
        params,
        method: 'GET'
    });
};

export const post = (url, params) => {
    // 发起 POST 请求逻辑
    return request({
        url,
        params,
        method: 'POST'
    });
};


const request = (config) => {
    const {
        url,
        params,
        dataType,
        showLoading = true,
        showError = true,
        errorCallback,
        responseType = responseTypeJson
    } = config;
    let contentType = contentTypeForm;
    let formData = params // 创建form对象
    if (dataType != null && dataType == 'json') {
        contentType = contentTypeJson;
    }
    let headers = {
        'Content-Type': contentType,
        'X-Requested-With': 'XMLHttpRequest',
        'token': uni.getStorageSync(LOCAL_STORAGE_KEY.token.key)
    }

    if (params) {
        for (let item in params) {
            if (params[item] == undefined) {
                params[item] = "";
            }
        }
    }
    return new Promise((resolve, reject) => {
        if (showLoading) {
            uni.showLoading();
        }
        uni.request({
            url: BASE_URL + url,
            data: params,
            header: headers,
            responseType: responseType,
            method: "POST",
        }).then(res => {
            if (showLoading) {
                uni.hideLoading();
            }
            uni.stopPullDownRefresh();
            if (res.statusCode != 200) {
                return Promise.reject("网络连接错误");
            }
            const responseData = res.data;
            if (responseType == "arraybuffer" || responseType == "blob") {
                resolve(responseData);
                return;
            }
            if (responseData.code == 200) {
                resolve(responseData);
                return;
            } else if (responseData.code == 901) {
                uni.navigateTo({
                    url: "/pages/account/LoginAndRegister"
                });
                return Promise.reject();
            } else {
                //其他错误
                if (errorCallback) {
                    errorCallback(responseData.info);
                }
                return Promise.reject(responseData.info);
            }
        }).catch(error => {
            if (error && showError) {
                Message.error(error);
            }
            return null;
        })
    })

};

export default request;