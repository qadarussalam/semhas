(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('JurusanDialogController', JurusanDialogController);

    JurusanDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Jurusan', 'Mahasiswa'];

    function JurusanDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Jurusan, Mahasiswa) {
        var vm = this;

        vm.jurusan = entity;
        vm.clear = clear;
        vm.save = save;
        vm.mahasiswas = Mahasiswa.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.jurusan.id !== null) {
                Jurusan.update(vm.jurusan, onSaveSuccess, onSaveError);
            } else {
                Jurusan.save(vm.jurusan, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('semhasApp:jurusanUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
