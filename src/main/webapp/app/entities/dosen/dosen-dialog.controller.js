(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('DosenDialogController', DosenDialogController);

    DosenDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Dosen', 'User'];

    function DosenDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Dosen, User) {
        var vm = this;

        vm.dosen = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.dosen.id !== null) {
                Dosen.update(vm.dosen, onSaveSuccess, onSaveError);
            } else {
                Dosen.save(vm.dosen, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('semhasApp:dosenUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
