(function() {
    'use strict';

    angular
        .module('semhasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dosen', {
            parent: 'entity',
            url: '/dosen?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'semhasApp.dosen.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dosen/dosens.html',
                    controller: 'DosenController',
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
                    $translatePartialLoader.addPart('dosen');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dosen-detail', {
            parent: 'dosen',
            url: '/dosen/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'semhasApp.dosen.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dosen/dosen-detail.html',
                    controller: 'DosenDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dosen');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Dosen', function($stateParams, Dosen) {
                    return Dosen.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dosen',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dosen-detail.edit', {
            parent: 'dosen-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dosen/dosen-dialog.html',
                    controller: 'DosenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dosen', function(Dosen) {
                            return Dosen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dosen.new', {
            parent: 'dosen',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dosen/dosen-dialog.html',
                    controller: 'DosenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nama: null,
                                nip: null,
                                email: null,
                                nomorTelepon: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dosen', null, { reload: 'dosen' });
                }, function() {
                    $state.go('dosen');
                });
            }]
        })
        .state('dosen.edit', {
            parent: 'dosen',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dosen/dosen-dialog.html',
                    controller: 'DosenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dosen', function(Dosen) {
                            return Dosen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dosen', null, { reload: 'dosen' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dosen.delete', {
            parent: 'dosen',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dosen/dosen-delete-dialog.html',
                    controller: 'DosenDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Dosen', function(Dosen) {
                            return Dosen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dosen', null, { reload: 'dosen' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
