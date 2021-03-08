module.exports = {
    publicPath: './',
    assetsDir: 'static',
    productionSourceMap: false,
    devServer: {
        proxy: {
            '/': {
                // target: 'http://127.0.0.1:54321',
                target: 'http://5f4b94f3b9de.ngrok.io',
                ws: true,
                pathRewrite: {
                    '^/': '/'
                }
            }
        }
    }
}