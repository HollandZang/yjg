import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
import './assets/css/icon.css';
import 'babel-polyfill';

Vue.config.productionTip = false;
Vue.use(ElementUI, {
    size: 'small'
});
//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title}`;
    let userdata = JSON.parse(localStorage.getItem('ms_user'));
    if (!userdata && to.path !== '/login') {
        next('/login');
    } else if (to.meta.permission) {
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        userdata.role === '管理员' ? next() : next('/403');
    } else {
        next();
    }
});

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
