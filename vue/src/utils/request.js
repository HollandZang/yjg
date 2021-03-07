import axios from 'axios';
import { Message } from 'element-ui';

let user= JSON.parse(localStorage.getItem('ms_user')) || {id:null};
const service = axios.create({
    // process.env.NODE_ENV === 'development' 来判断是否开发环境
    // easy-mock服务挂了，暂时不使用了
    // baseURL: 'https://www.easy-mock.com/mock/592501a391470c0ac1fab128',
    timeout: 10000,
    headers: {
        'Accept': 'application/json, text/plain, */*',
        'userid': user.id,
    }
});

service.interceptors.request.use(
    config => {
        if (!config.url.startsWith("http")) {
            config.url = "http://192.168.1.6:54321" + config.url;
        }
        return config;
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    response => {
        const status = Number(response.status) || 200
        if (status === 200) {
            if (response.data.code !== 0)
                Message({
                    type: 'error',
                    message: response.data.msg
                })
            return response.data;
        } else {
            if (status === 404) {
                // 请求资源不存在
                Message({
                    type: 'error',
                    message: '请求资源不存在'
                })
            }
            if (status === 403) {
                // 当前操作没有权限
                Message({
                    type: 'error',
                    message: '当前操作没有权限'
                })
            }
            Promise.reject();
        }
    },
    error => {
        Message({
            type: 'error',
            message: error,
        })
        return Promise.reject();
    }
);

export default service;
