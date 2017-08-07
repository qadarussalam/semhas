(function() {
    'use strict';

    angular
        .module('semhasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    $translatePartialLoader.addPart('seminar');
                    $translatePartialLoader.addPart('mahasiswa');
                    $translatePartialLoader.addPart('statusSeminar');
                    $translatePartialLoader.addPart('pesertaSeminar');
                    $translatePartialLoader.addPart('absensiSeminar');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
