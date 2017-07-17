(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('MahasiswaDeleteController',MahasiswaDeleteController);

    MahasiswaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mahasiswa'];

    function MahasiswaDeleteController($uibModalInstance, entity, Mahasiswa) {
        var vm = this;

        vm.mahasiswa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Mahasiswa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
