(function() {
    'use strict';

    angular
        .module('semhasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sesi', {
            parent: 'entity',
            url: '/sesi?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ADMIN_RUANG'],
                pageTitle: 'semhasApp.sesi.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sesi/sesis.html',
                    controller: 'SesiController',
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
                    $translatePartialLoader.addPart('sesi');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sesi-detail', {
            parent: 'sesi',
            url: '/sesi/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ADMIN_RUANG'],
                pageTitle: 'semhasApp.sesi.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sesi/sesi-detail.html',
                    controller: 'SesiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sesi');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Sesi', function($stateParams, Sesi) {
                    return Sesi.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sesi',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sesi-detail.edit', {
            parent: 'sesi-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ADMIN_RUANG']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sesi/sesi-dialog.html',
                    controller: 'SesiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sesi', function(Sesi) {
                            return Sesi.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sesi.new', {
            parent: 'sesi',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ADMIN_RUANG']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sesi/sesi-dialog.html',
                    controller: 'SesiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                urutan: null,
                                jamMulai: null,
                                jamSelesai: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sesi', null, { reload: 'sesi' });
                }, function() {
                    $state.go('sesi');
                });
            }]
        })
        .state('sesi.edit', {
            parent: 'sesi',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ADMIN_RUANG']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sesi/sesi-dialog.html',
                    controller: 'SesiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sesi', function(Sesi) {
                            return Sesi.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sesi', null, { reload: 'sesi' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sesi.delete', {
            parent: 'sesi',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ADMIN_RUANG']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sesi/sesi-delete-dialog.html',
                    controller: 'SesiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sesi', function(Sesi) {
                            return Sesi.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sesi', null, { reload: 'sesi' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
