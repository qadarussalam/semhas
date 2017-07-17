(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('SeminarDeleteController',SeminarDeleteController);

    SeminarDeleteController.$inject = ['$uibModalInstance', 'entity', 'Seminar'];

    function SeminarDeleteController($uibModalInstance, entity, Seminar) {
        var vm = this;

        vm.seminar = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Seminar.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
