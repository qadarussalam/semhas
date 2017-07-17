(function() {
    'use strict';

    angular
        .module('semhasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('jadwal-seminar', {
            parent: 'entity',
            url: '/jadwal-seminar?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'semhasApp.jadwalSeminar.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jadwal-seminar/jadwal-seminars.html',
                    controller: 'JadwalSeminarController',
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
                    $translatePartialLoader.addPart('jadwalSeminar');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('jadwal-seminar-detail', {
            parent: 'jadwal-seminar',
            url: '/jadwal-seminar/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'semhasApp.jadwalSeminar.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jadwal-seminar/jadwal-seminar-detail.html',
                    controller: 'JadwalSeminarDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('jadwalSeminar');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'JadwalSeminar', function($stateParams, JadwalSeminar) {
                    return JadwalSeminar.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'jadwal-seminar',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('jadwal-seminar-detail.edit', {
            parent: 'jadwal-seminar-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jadwal-seminar/jadwal-seminar-dialog.html',
                    controller: 'JadwalSeminarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JadwalSeminar', function(JadwalSeminar) {
                            return JadwalSeminar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jadwal-seminar.new', {
            parent: 'jadwal-seminar',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jadwal-seminar/jadwal-seminar-dialog.html',
                    controller: 'JadwalSeminarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tanggal: null,
                                tersedia: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('jadwal-seminar', null, { reload: 'jadwal-seminar' });
                }, function() {
                    $state.go('jadwal-seminar');
                });
            }]
        })
        .state('jadwal-seminar.edit', {
            parent: 'jadwal-seminar',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jadwal-seminar/jadwal-seminar-dialog.html',
                    controller: 'JadwalSeminarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JadwalSeminar', function(JadwalSeminar) {
                            return JadwalSeminar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jadwal-seminar', null, { reload: 'jadwal-seminar' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jadwal-seminar.delete', {
            parent: 'jadwal-seminar',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jadwal-seminar/jadwal-seminar-delete-dialog.html',
                    controller: 'JadwalSeminarDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['JadwalSeminar', function(JadwalSeminar) {
                            return JadwalSeminar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jadwal-seminar', null, { reload: 'jadwal-seminar' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
