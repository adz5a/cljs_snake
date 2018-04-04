"use strict";

const webpack = require("webpack");
const { resolve } = require("path");
const { DefinePlugin } = webpack;

const { NODE_ENV = "production" } = process.env;

const config = {
  entry: {
    lib: resolve(__dirname, "src/js/lib"),
  },
  output: {
    path: resolve(__dirname, "resources/public/js"),
    filename: "[name].js",
    libraryTarget: "global",
  },
  plugins: [
    new DefinePlugin({
      // cf https://webpack.js.org/plugins/define-plugin/#feature-flags
      "process.env.NODE_ENV": NODE_ENV
    })
  ],

  module: {
    rules: [{
        test: /\.jsx?/,
        include: [resolve(__dirname, "src/js")]
      }],
  }
};


webpack(config, (err) => {
  if (err) throw err;
  console.log("done");
});
