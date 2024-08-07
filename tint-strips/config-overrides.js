// config-overrides.js
module.exports = function override(config, env) {
    // Añadir yaml-loader
    config.module.rules.push({
      test: /\.yaml$/,
      use: 'yaml-loader'
    });
    
    return config;
  };
  