(function() {
    'use strict';

    angular
        .module('semhasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ruang', {
            parent: 'entity',
            url: '/ruang?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ADMIN_RUANG'],
                pageTitle: 'semhasApp.ruang.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ruang/ruangs.html',
                    controller: 'RuangController',
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
                    $translatePartialLoader.addPart('ruang');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ruang-detail', {
            parent: 'ruang',
            url: '/ruang/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ADMIN_RUANG'],
                pageTitle: 'semhasApp.ruang.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ruang/ruang-detail.html',
                    controller: 'RuangDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ruang');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ruang', function($stateParams, Ruang) {
                    return Ruang.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ruang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ruang-detail.edit', {
            parent: 'ruang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ADMIN_RUANG']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ruang/ruang-dialog.html',
                    controller: 'RuangDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ruang', function(Ruang) {
                            return Ruang.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ruang.new', {
            parent: 'ruang',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ADMIN_RUANG']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ruang/ruang-dialog.html',
                    controller: 'RuangDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nama: null,
                                kapasitas: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ruang', null, { reload: 'ruang' });
                }, function() {
                    $state.go('ruang');
                });
            }]
        })
        .state('ruang.edit', {
            parent: 'ruang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ruang/ruang-dialog.html',
                    controller: 'RuangDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ruang', function(Ruang) {
                            return Ruang.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ruang', null, { reload: 'ruang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ruang.delete', {
            parent: 'ruang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ADMIN_RUANG']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ruang/ruang-delete-dialog.html',
                    controller: 'RuangDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ruang', function(Ruang) {
                            return Ruang.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ruang', null, { reload: 'ruang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
