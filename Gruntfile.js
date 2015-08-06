module.exports = function (grunt) {

    // Load the plugin that provides the "uglify" task.
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-react');

    grunt.initConfig({
        react: {
            jsx: {
                files: {
                    'resources/build/js/app.js': ['resources/js/**/*.jsx']
                }
            }
        },

        copy: {
            lib: {
                files: [
                    {
                        expand: true,
                        cwd: 'node_modules/react/dist/',
                        src: ['react.js'],
                        dest: 'resources/lib/'
                    }
                ]
            },
            public: {
                files: [
                    {
                        expand: true,
                        cwd: 'resources/',
                        src: ['index.html'],
                        dest: 'public/'
                    },
                    {
                        expand: true,
                        cwd: 'resources/build/',
                        src: ['js/**/*.js', '**/*.html'],
                        dest: 'public/'
                    },
                    {
                        expand: true,
                        cwd: 'resources/lib/',
                        src: ['**/*.js'],
                        dest: 'public/js'
                    }
                ]
            }
        },

        watch: {
            react: {
                files: ['resources/js/**.jsx'],
                tasks: ['react']
            },
            build: {
                files: ['resources/build/**/*'],
                tasks: ['copy']
            }
        }

    });

    // Default task(s).
    grunt.registerTask('default', ['build', 'watch']);
    grunt.registerTask('build', ['react', 'copy']);

};