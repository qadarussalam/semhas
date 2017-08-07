(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('SeminarPesertaDialogController',SeminarPesertaDialogController);

    SeminarPesertaDialogController.$inject = ['$uibModalInstance', 'seminar', 'dosen', 'Seminar', 'Mahasiswa', 'PesertaSeminar'];

    function SeminarPesertaDialogController($uibModalInstance, seminar, dosen, Seminar, Mahasiswa, PesertaSeminar) {
        var vm = this;

        vm.seminar = seminar;

        vm.setActive = setActive;
        vm.clear = clear;

        loadAll();
        function loadAll() {
            Seminar.getPeserta({
                id: seminar.id
            }, function(data) {
                vm.pesertaSeminars = data;

                angular.forEach(data, function(seminar) {
                    Mahasiswa.get({id: seminar.mahasiswaId}, function(mhs) {
                        seminar.mahasiswa = mhs;
                    });
                });
            });
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function setActive (pesertaSeminar, absensi) {
            pesertaSeminar.absensi = absensi;
            PesertaSeminar.update(pesertaSeminar);
        }
    }
})();
