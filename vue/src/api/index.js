import request from '../utils/request';

export const login = query => {
    return request({
        url: '/user/login',
        method: 'POST',
        params: query
    });
};

export const loginout = query => {
    return request({
        url: '/user/logout',
        method: 'POST',
        params: query
    });
};

export const addUser = query => {
    return request({
        url: '/user/add',
        method: 'POST',
        params: query
    });
};

export const getAllUser = ()=> {
    return request({
        url: '/user/list',
        method: 'GET',
    });
};