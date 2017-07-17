(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('JurusanDeleteController',JurusanDeleteController);

    JurusanDeleteController.$inject = ['$uibModalInstance', 'entity', 'Jurusan'];

    function JurusanDeleteController($uibModalInstance, entity, Jurusan) {
        var vm = this;

        vm.jurusan = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Jurusan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
