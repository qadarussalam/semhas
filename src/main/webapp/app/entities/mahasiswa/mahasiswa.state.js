(function() {
    'use strict';

    angular
        .module('semhasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mahasiswa', {
            parent: 'entity',
            url: '/mahasiswa?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'semhasApp.mahasiswa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mahasiswa/mahasiswas.html',
                    controller: 'MahasiswaController',
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
                    $translatePartialLoader.addPart('mahasiswa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mahasiswa-detail', {
            parent: 'mahasiswa',
            url: '/mahasiswa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'semhasApp.mahasiswa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mahasiswa/mahasiswa-detail.html',
                    controller: 'MahasiswaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mahasiswa');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Mahasiswa', function($stateParams, Mahasiswa) {
                    return Mahasiswa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mahasiswa',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mahasiswa-detail.edit', {
            parent: 'mahasiswa-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mahasiswa/mahasiswa-dialog.html',
                    controller: 'MahasiswaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mahasiswa', function(Mahasiswa) {
                            return Mahasiswa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mahasiswa.new', {
            parent: 'mahasiswa',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mahasiswa/mahasiswa-dialog.html',
                    controller: 'MahasiswaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nama: null,
                                nim: null,
                                semester: null,
                                email: null,
                                nomorTelepon: null,
                                foto: null,
                                fotoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mahasiswa', null, { reload: 'mahasiswa' });
                }, function() {
                    $state.go('mahasiswa');
                });
            }]
        })
        .state('mahasiswa.edit', {
            parent: 'mahasiswa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mahasiswa/mahasiswa-dialog.html',
                    controller: 'MahasiswaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mahasiswa', function(Mahasiswa) {
                            return Mahasiswa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mahasiswa', null, { reload: 'mahasiswa' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mahasiswa.delete', {
            parent: 'mahasiswa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mahasiswa/mahasiswa-delete-dialog.html',
                    controller: 'MahasiswaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mahasiswa', function(Mahasiswa) {
                            return Mahasiswa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mahasiswa', null, { reload: 'mahasiswa' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
