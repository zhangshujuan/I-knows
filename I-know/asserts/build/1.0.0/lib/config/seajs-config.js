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
            // plugins
            "rails": 'plugins/jquery-ujs/rails',
            "cxselect": 'plugins/cxselect/cxselect',
            "flexSlider": 'plugins/flexslider/jqueryflexslider',
            "scrollfix": 'plugins/scrollfix/scrollfix',
            "modal":'plugins/modal/modal',
            //scripts
            "gaoding": 'scripts/mybase/gaoding',
            "confirm": 'scripts/mybase/confirm',

            "core":'scripts/utils/core',
            "dataBind":'scripts/databind/data-bind',
            //
            "wx":"http://res.wx.qq.com/open/js/{wxVersion}"
        },
        paths: {
            utilsPath: 'scripts/utils'
        },
        comboSyntax: ["??", ","],
        comboMaxLength: 500,
           preload: [
                'gaoding'
           ],
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