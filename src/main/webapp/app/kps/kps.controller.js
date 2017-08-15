(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('KpsController', KpsController);

    KpsController.$inject = ['$rootScope', '$state', '$localStorage', '$uibModal', 'PesertaSeminar', 'Seminar', 'Mahasiswa'];

    function KpsController($rootScope, $state, $localStorage, $uibModal, PesertaSeminar, Seminar, Mahasiswa) {
        var vm = this;
        
        vm.openDialogPrint = openDialogPrint;

        var token = $localStorage.authenticationToken;
        var data = jwt_decode(token);

        vm.mahasiswaId = data['semhas.mhsw'];

        Mahasiswa.getKps({
            id: vm.mahasiswaId
        }, function(data) {
            vm.seminars = data.seminars;
            
            angular.forEach(data.seminars, function(seminar) {
                seminar.jadwalSeminarTanggal = moment(seminar.jadwalSeminarTanggal).format('DD MMMM YYYY');
                seminar.jamMulai = moment(seminar.jamMulai).format('HH:mm');
                seminar.jamSelesai = moment(seminar.jamSelesai).format('HH:mm');

                Mahasiswa.get({id: seminar.mahasiswaId}, function(mhs) {
                    seminar.mahasiswa = mhs;
                });
            });
        });
        
        function openDialogPrint() {
            PesertaSeminar.printKps({
                id: vm.mahasiswaId
            }, function(data) {
                var modalInstance = $uibModal.open({
                    templateUrl: 'app/layouts/print/print-dialog.html',
                    controller: 'PrintDialogController',
                    controllerAs: 'vm',
                    size: 'lg',
                    resolve: {
                        template: function () {
                            return data.html;
                        },
                        title: function() {
                            return 'kartu-peserta-seminar.pdf'
                        }
                    }
                });
            });
        }
    }
})();
