(function() {
    'use strict';

    angular
        .module('semhasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('peserta-seminar', {
            parent: 'entity',
            url: '/peserta-seminar?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'semhasApp.pesertaSeminar.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/peserta-seminar/peserta-seminars.html',
                    controller: 'PesertaSeminarController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pesertaSeminar');
                    $translatePartialLoader.addPart('absensiSeminar');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('peserta-seminar-detail', {
            parent: 'peserta-seminar',
            url: '/peserta-seminar/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'semhasApp.pesertaSeminar.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/peserta-seminar/peserta-seminar-detail.html',
                    controller: 'PesertaSeminarDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pesertaSeminar');
                    $translatePartialLoader.addPart('absensiSeminar');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PesertaSeminar', function($stateParams, PesertaSeminar) {
                    return PesertaSeminar.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'peserta-seminar',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('peserta-seminar-detail.edit', {
            parent: 'peserta-seminar-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/peserta-seminar/peserta-seminar-dialog.html',
                    controller: 'PesertaSeminarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PesertaSeminar', function(PesertaSeminar) {
                            return PesertaSeminar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('peserta-seminar.new', {
            parent: 'peserta-seminar',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/peserta-seminar/peserta-seminar-dialog.html',
                    controller: 'PesertaSeminarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                absensi: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('peserta-seminar', null, { reload: 'peserta-seminar' });
                }, function() {
                    $state.go('peserta-seminar');
                });
            }]
        })
        .state('peserta-seminar.edit', {
            parent: 'peserta-seminar',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/peserta-seminar/peserta-seminar-dialog.html',
                    controller: 'PesertaSeminarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PesertaSeminar', function(PesertaSeminar) {
                            return PesertaSeminar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('peserta-seminar', null, { reload: 'peserta-seminar' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('peserta-seminar.delete', {
            parent: 'peserta-seminar',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/peserta-seminar/peserta-seminar-delete-dialog.html',
                    controller: 'PesertaSeminarDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PesertaSeminar', function(PesertaSeminar) {
                            return PesertaSeminar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('peserta-seminar', null, { reload: 'peserta-seminar' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
