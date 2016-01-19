(function() {
    var root = this;
    var assetsPath = "";
    var config = {
        base: typeof process === "undefined" ? window.HEALTH.assetsPath : null,
		vars: {
		    'jqueryVersion':'1-8-3',
            'wxVersion':'jweixin-1.0.0'
		},
        alias: {

            // lib
            "$": 'lib/jquery/jquery-{jqueryVersion}',
            "jQuery": 'lib/jquery/jquery-{jqueryVersion}',
            "jquery": 'lib/jquery/jquery-{jqueryVersion}',
            "modal":'plugins/modal/modal',
            "wx":"http://res.wx.qq.com/open/js/{wxVersion}"
        },
        paths: {
            utilsPath: 'scripts/utils'
        },
        map: [],
        charset: 'utf-8',
        timeout: 20000,
        debug: true
    };
    if (root.seajs) {
        root.seajs.config(config);
    }
    return config;
}).call(this);