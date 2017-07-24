(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('RuangDialogController', RuangDialogController);

    RuangDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Ruang', 'User', 'JadwalSeminar'];

    function RuangDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Ruang, User, JadwalSeminar) {
        var vm = this;

        vm.ruang = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query({
            role: 'ROLE_ADMIN_RUANG'
        });
        vm.jadwalseminars = JadwalSeminar.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ruang.id !== null) {
                Ruang.update(vm.ruang, onSaveSuccess, onSaveError);
            } else {
                Ruang.save(vm.ruang, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('semhasApp:ruangUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
