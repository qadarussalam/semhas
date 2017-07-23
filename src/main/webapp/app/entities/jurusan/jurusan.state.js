(function() {
    'use strict';

    angular
        .module('semhasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('jurusan', {
            parent: 'entity',
            url: '/jurusan?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'semhasApp.jurusan.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jurusan/jurusans.html',
                    controller: 'JurusanController',
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
                    $translatePartialLoader.addPart('jurusan');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('jurusan-detail', {
            parent: 'jurusan',
            url: '/jurusan/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'semhasApp.jurusan.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jurusan/jurusan-detail.html',
                    controller: 'JurusanDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('jurusan');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Jurusan', function($stateParams, Jurusan) {
                    return Jurusan.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'jurusan',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('jurusan-detail.edit', {
            parent: 'jurusan-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jurusan/jurusan-dialog.html',
                    controller: 'JurusanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jurusan', function(Jurusan) {
                            return Jurusan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jurusan.new', {
            parent: 'jurusan',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jurusan/jurusan-dialog.html',
                    controller: 'JurusanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nama: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('jurusan', null, { reload: 'jurusan' });
                }, function() {
                    $state.go('jurusan');
                });
            }]
        })
        .state('jurusan.edit', {
            parent: 'jurusan',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jurusan/jurusan-dialog.html',
                    controller: 'JurusanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jurusan', function(Jurusan) {
                            return Jurusan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jurusan', null, { reload: 'jurusan' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jurusan.delete', {
            parent: 'jurusan',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jurusan/jurusan-delete-dialog.html',
                    controller: 'JurusanDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Jurusan', function(Jurusan) {
                            return Jurusan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jurusan', null, { reload: 'jurusan' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
