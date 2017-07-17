(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('PesertaSeminarDialogController', PesertaSeminarDialogController);

    PesertaSeminarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PesertaSeminar', 'Mahasiswa', 'Seminar'];

    function PesertaSeminarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PesertaSeminar, Mahasiswa, Seminar) {
        var vm = this;

        vm.pesertaSeminar = entity;
        vm.clear = clear;
        vm.save = save;
        vm.mahasiswas = Mahasiswa.query();
        vm.seminars = Seminar.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pesertaSeminar.id !== null) {
                PesertaSeminar.update(vm.pesertaSeminar, onSaveSuccess, onSaveError);
            } else {
                PesertaSeminar.save(vm.pesertaSeminar, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('semhasApp:pesertaSeminarUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
