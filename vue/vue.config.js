module.exports = {
    publicPath: './',
    assetsDir: 'static',
    productionSourceMap: false,
    devServer: {
        proxy: {
            '/': {
                target: 'http://127.0.0.1:54321',
                // target: 'http://6224e3584acf.ngrok.io',
                ws: true,
                pathRewrite: {
                    '^/': '/'
                }
            }
        }
    }
}