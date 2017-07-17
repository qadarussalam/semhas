(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('RuangDeleteController',RuangDeleteController);

    RuangDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ruang'];

    function RuangDeleteController($uibModalInstance, entity, Ruang) {
        var vm = this;

        vm.ruang = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ruang.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
