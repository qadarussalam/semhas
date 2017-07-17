(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('JadwalSeminarDeleteController',JadwalSeminarDeleteController);

    JadwalSeminarDeleteController.$inject = ['$uibModalInstance', 'entity', 'JadwalSeminar'];

    function JadwalSeminarDeleteController($uibModalInstance, entity, JadwalSeminar) {
        var vm = this;

        vm.jadwalSeminar = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            JadwalSeminar.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
