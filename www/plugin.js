
var exec = require('cordova/exec');

var PLUGIN_NAME = 'StorageOptions';

// "scb"            -> success callback
// "ecb"            -> error callback
// "PLUGIN_NAME"    -> Java Class
// "METHOD_NAME"    -> Method
// []               -> Parameter
// exec(scb, ecb, PLUGIN_NAME, 'METHOD_NAME', [phrase]);


var StorageOptions = {

    getStoragePath: function (speicherort, cb) {
        exec(cb, null, PLUGIN_NAME, 'getStoragePath', [speicherort]);
    },
    getFreeMemory: function (speicherort, cb) {
        exec(cb, null, PLUGIN_NAME, 'getFreeMemory', [speicherort]);
    },
    getFreeBytes: function (speicherort, cb) {
        exec(cb, null, PLUGIN_NAME, 'getFreeBytes', [speicherort]);
    }

};

module.exports = StorageOptions;
