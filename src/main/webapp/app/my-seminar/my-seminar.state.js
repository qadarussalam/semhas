(function() {
    'use strict';

    angular
        .module('semhasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('my-seminar', {
            parent: 'app',
            url: '/my-seminar',
            data: {
                authorities: ['ROLE_MAHASISWA'],
                pageTitle: 'semhasApp.mySeminar.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/my-seminar/my-seminar.html',
                    controller: 'MySeminarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mySeminar');
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('seminar');
                    $translatePartialLoader.addPart('mahasiswa');
                    $translatePartialLoader.addPart('statusSeminar');
                    return $translate.refresh();
                }]
            }
        })
        .state('my-seminar.new', {
            parent: 'my-seminar',
            url: '/new',
            data: {
                authorities: ['ROLE_MAHASISWA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/my-seminar/my-seminar-dialog.html',
                    controller: 'MySeminarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                judul: null,
                                abstrak: null,
                                fileAccSeminar: null,
                                fileAccSeminarContentType: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('my-seminar', null, { reload: 'my-seminar' });
                }, function() {
                    $state.go('my-seminar');
                });
            }]
        });
    }

})();
