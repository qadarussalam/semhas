(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('JadwalSeminarDialogController', JadwalSeminarDialogController);

    JadwalSeminarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'JadwalSeminar', 'Sesi', 'Ruang', 'Seminar'];

    function JadwalSeminarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, JadwalSeminar, Sesi, Ruang, Seminar) {
        var vm = this;

        vm.jadwalSeminar = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.sesis = Sesi.query();
        vm.ruangs = Ruang.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.jadwalSeminar.id !== null) {
                JadwalSeminar.update(vm.jadwalSeminar, onSaveSuccess, onSaveError);
            } else {
                JadwalSeminar.save(vm.jadwalSeminar, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('semhasApp:jadwalSeminarUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.tanggal = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
