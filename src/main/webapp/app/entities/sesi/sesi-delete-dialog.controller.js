(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('SesiDeleteController',SesiDeleteController);

    SesiDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sesi'];

    function SesiDeleteController($uibModalInstance, entity, Sesi) {
        var vm = this;

        vm.sesi = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sesi.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
