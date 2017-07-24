(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$filter', 'Principal', 'LoginService', '$state', '$uibModal', 'Seminar', 'Mahasiswa'];

    function HomeController ($scope, $filter, Principal, LoginService, $state, $uibModal, Seminar, Mahasiswa) {
        var vm = this;

        vm.openSeminarAccDialog = openSeminarAccDialog;

        vm.role = null;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;

                var token = localStorage['jhi-authenticationToken'];
                var data = jwt_decode(token);
                
                vm.mahasiswaId = data['semhas.mhsw'];

                var filters = $filter('filter')(account.authorities, 'ROLE_MAHASISWA');
                if(filters.length > 0) {
                    Seminar.query({
                        status: 'DISETUJUI',
                        dosenId: ''
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

                var filters = $filter('filter')(account.authorities, 'ROLE_PRODI');
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
            });
        }
        function register () {
            $state.go('register');
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
                $state.go('^');
            });
        }
    }
})();
