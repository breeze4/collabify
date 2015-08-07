module.exports = {
    context: __dirname + "/resources",
    entry: "./js/main.jsx",

    output: {
        filename: "app.js",
        path: __dirname + "/public/js"
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