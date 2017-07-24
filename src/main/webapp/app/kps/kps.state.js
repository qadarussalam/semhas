(function() {
    'use strict';

    angular
        .module('semhasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('kps', {
            parent: 'app',
            url: '/kps',
            data: {
                authorities: ['ROLE_MAHASISWA'],
                pageTitle: 'semhasApp.kps.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/kps/kps.html',
                    controller: 'KpsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('kps');
                    $translatePartialLoader.addPart('seminar');
                    $translatePartialLoader.addPart('mahasiswa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
        // .state('my-seminar.new', {
        //     parent: 'my-seminar',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_MAHASISWA']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/my-seminar/my-seminar-dialog.html',
        //             controller: 'MySeminarDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         judul: null,
        //                         abstrak: null,
        //                         fileAccSeminar: null,
        //                         fileAccSeminarContentType: null,
        //                         status: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('my-seminar', null, { reload: 'my-seminar' });
        //         }, function() {
        //             $state.go('my-seminar');
        //         });
        //     }]
        // });
    }

})();
