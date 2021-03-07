import request from '../utils/request';
export const add = query => {
    return request({
        url: '/order/add',
        method: 'POST',
        params: query
    });
};
export const list = (query)=> {
    return request({
        url: '/order/list',
        method: 'GET',
        params: query
    });
};
export const update = (query)=> {
    return request({
        url: '/order/update',
        method: 'get',
        params: query
    });
};
export const claim = (query)=> {
    return request({
        url: '/order/claim',
        method: 'POST',
        params: query
    });
};