(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('SeminarJoinDialogController',SeminarJoinDialogController);

    SeminarJoinDialogController.$inject = ['$uibModalInstance', 'seminar', 'mahasiswa', 'PesertaSeminar'];

    function SeminarJoinDialogController($uibModalInstance, seminar, mahasiswa, PesertaSeminar) {
        var vm = this;

        vm.seminar = seminar;
        vm.clear = clear;
        vm.confirmJoin = confirmJoin;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmJoin () {
            PesertaSeminar.save({
                absensi: 'ABSEN',
                mahasiswaId: mahasiswa,
                seminar: seminar
            }, function () {
                $uibModalInstance.close(true);
            });
        }
    }
})();
