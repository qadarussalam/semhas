(function() {
    'use strict';

    angular
        .module('semhasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('seminar', {
            parent: 'entity',
            url: '/seminar?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'semhasApp.seminar.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seminar/seminars.html',
                    controller: 'SeminarController',
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
                    $translatePartialLoader.addPart('seminar');
                    $translatePartialLoader.addPart('statusSeminar');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('seminar-detail', {
            parent: 'seminar',
            url: '/seminar/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'semhasApp.seminar.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seminar/seminar-detail.html',
                    controller: 'SeminarDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('seminar');
                    $translatePartialLoader.addPart('statusSeminar');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Seminar', function($stateParams, Seminar) {
                    return Seminar.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'seminar',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('seminar-detail.edit', {
            parent: 'seminar-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seminar/seminar-dialog.html',
                    controller: 'SeminarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Seminar', function(Seminar) {
                            return Seminar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seminar.new', {
            parent: 'seminar',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seminar/seminar-dialog.html',
                    controller: 'SeminarDialogController',
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
                                ruangan: null,
                                jamMulai: null,
                                jamSelesai: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('seminar', null, { reload: 'seminar' });
                }, function() {
                    $state.go('seminar');
                });
            }]
        })
        .state('seminar.edit', {
            parent: 'seminar',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seminar/seminar-dialog.html',
                    controller: 'SeminarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Seminar', function(Seminar) {
                            return Seminar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seminar', null, { reload: 'seminar' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seminar.delete', {
            parent: 'seminar',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seminar/seminar-delete-dialog.html',
                    controller: 'SeminarDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Seminar', function(Seminar) {
                            return Seminar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seminar', null, { reload: 'seminar' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
