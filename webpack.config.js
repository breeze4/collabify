var path = require('path');

module.exports = {
    context: path.join(__dirname, "resources"),
    entry: "./js/main.jsx",

    output: {
        filename: "app.js",
        path:path.join(__dirname, "public", "js")
    },
    module: {
        loaders: [
            {
                test: /\.jsx$/,
                exclude: /node_modules/,
                loaders: ["babel-loader"]
            }
        ]
    }
}