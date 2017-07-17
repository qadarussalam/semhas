(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('PesertaSeminarDeleteController',PesertaSeminarDeleteController);

    PesertaSeminarDeleteController.$inject = ['$uibModalInstance', 'entity', 'PesertaSeminar'];

    function PesertaSeminarDeleteController($uibModalInstance, entity, PesertaSeminar) {
        var vm = this;

        vm.pesertaSeminar = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PesertaSeminar.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
