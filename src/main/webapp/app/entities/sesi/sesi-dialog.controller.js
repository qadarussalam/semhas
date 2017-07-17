(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('SesiDialogController', SesiDialogController);

    SesiDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sesi', 'JadwalSeminar'];

    function SesiDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sesi, JadwalSeminar) {
        var vm = this;

        vm.sesi = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.jadwalseminars = JadwalSeminar.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sesi.id !== null) {
                Sesi.update(vm.sesi, onSaveSuccess, onSaveError);
            } else {
                Sesi.save(vm.sesi, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('semhasApp:sesiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.jamMulai = false;
        vm.datePickerOpenStatus.jamSelesai = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
