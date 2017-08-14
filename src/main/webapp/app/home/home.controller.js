(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$filter', 'Principal', 'LoginService', '$state', '$uibModal', 'Seminar', 'Mahasiswa'];

    function HomeController ($scope, $filter, Principal, LoginService, $state, $uibModal, Seminar, Mahasiswa) {
        var vm = this;

        vm.openSeminarAccDialog = openSeminarAccDialog;
        vm.openSeminarJoinDialog = openSeminarJoinDialog;
        vm.openSeminarPesertaDialog = openSeminarPesertaDialog;

        vm.role = null;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.login = LoginService.open;
        
        $scope.$on('authenticationSuccess', function() {
            getAccount();
            vm.isAuthenticated = Principal.isAuthenticated;
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                
                if(account) {
                    getSeminar(account);
                }
            });
        }
        
        function getSeminar(account) {
            // User is Mahasiswa
            var filters = $filter('filter')(account.authorities, 'ROLE_MAHASISWA', true);
            if(filters.length > 0) {
                var token = localStorage['jhi-authenticationToken'];
                var data = jwt_decode(token);

                vm.mahasiswaId = data['semhas.mhsw'];

                Seminar.query({
                    q: '',
                    'not-registered-by': vm.mahasiswaId,
                    status: 'DISETUJUI'
                // Seminar.query({
                //     status: 'DISETUJUI',
                //     dosenId: ''
                }, function(data) {
                    vm.seminars = data;

                    angular.forEach(data, function(seminar) {
                        Mahasiswa.get({id: seminar.mahasiswaId}, function(mhs) {
                            seminar.mahasiswa = mhs;
                        });
                    });

                    vm.role = 'ROLE_MAHASISWA';
                });
            }

            // User is Prodi
            var filters = $filter('filter')(account.authorities, 'ROLE_PRODI', true);
            if(filters.length > 0) {
                Seminar.query({
                    status: 'MENUNGGU_PERSETUJUAN',
                    dosenId: ''
                }, function(data) {
                    vm.seminars = data;

                    angular.forEach(data, function(seminar) {
                        Mahasiswa.get({id: seminar.mahasiswaId}, function(mhs) {
                            seminar.mahasiswa = mhs;
                        });
                    });

                    vm.role = 'ROLE_PRODI';
                });
            }

            // User is Dosen
            var filters = $filter('filter')(account.authorities, 'ROLE_DOSEN', true);
            if(filters.length > 0) {
                var token = localStorage['jhi-authenticationToken'];
                var data = jwt_decode(token);

                vm.dosenId = data['semhas.dosn'];
                
                Seminar.query({
                    status: 'DISETUJUI',
                    dosenId: vm.dosenId
                }, function(data) {
                    vm.seminars = data;

                    angular.forEach(data, function(seminar) {
                        Mahasiswa.get({id: seminar.mahasiswaId}, function(mhs) {
                            seminar.mahasiswa = mhs;
                        });
                    });

                    vm.role = 'ROLE_DOSEN';
                });
            }
        }

        function openSeminarJoinDialog(seminar) {
            var modalInstance = $uibModal.open({
                templateUrl: 'app/entities/seminar/seminar-join-dialog.html',
                controller: 'SeminarJoinDialogController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    seminar: function () {
                        return seminar;
                    },
                    mahasiswa: function () {
                        return vm.mahasiswaId;
                    }
                }
            });

            modalInstance.result.then(function (data) {
                $state.go('home', null, { reload: 'home' });
            }, function() {
                $state.go('home');
            });
        }

        function openSeminarAccDialog(seminar) {
            var modalInstance = $uibModal.open({
                templateUrl: 'app/entities/seminar/seminar-acc-dialog.html',
                controller: 'SeminarAccDialogController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    seminar: function () {
                        return seminar;
                    }
                }
            });

            modalInstance.result.then(function (data) {
                $state.go('home', null, { reload: 'home' });
            }, function() {
                $state.go('home');
            });
        }

        function openSeminarPesertaDialog(seminar) {
            var modalInstance = $uibModal.open({
                templateUrl: 'app/entities/seminar/seminar-peserta-dialog.html',
                controller: 'SeminarPesertaDialogController',
                controllerAs: 'vm',
                size: 'lg',
                resolve: {
                    seminar: function () {
                        return seminar;
                    },
                    dosen: function () {
                        return vm.dosenId;
                    }
                }
            });

            modalInstance.result.then(function (data) {
                $state.go('home', null, { reload: 'home' });
            }, function() {
                $state.go('home');
            });
        }
    }
})();
