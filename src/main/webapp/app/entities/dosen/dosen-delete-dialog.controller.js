(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('DosenDeleteController',DosenDeleteController);

    DosenDeleteController.$inject = ['$uibModalInstance', 'entity', 'Dosen'];

    function DosenDeleteController($uibModalInstance, entity, Dosen) {
        var vm = this;

        vm.dosen = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Dosen.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
