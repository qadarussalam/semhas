(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('SeminarAccDialogController', SeminarAccDialogController);

    SeminarAccDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'seminar', 'Seminar', 'Mahasiswa', 'Dosen', 'JadwalSeminar', 'Sesi'];

    function SeminarAccDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, seminar, Seminar, Mahasiswa, Dosen, JadwalSeminar, Sesi) {
        var vm = this;

        vm.clear = clear;
        vm.save = save;
        vm.seminar = seminar;
        vm.openCalendar = openCalendar;
        vm.openFile = DataUtils.openFile;
        vm.selectJadwalSeminar = selectJadwalSeminar;

        vm.dosens = Dosen.query();
        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.jamMulai = false;
        vm.datePickerOpenStatus.jamSelesai = false;

        JadwalSeminar.query({
            filter: 'tersedia',
            size: 1
        }, function(data, headers) {
            var totalItems = headers('X-Total-Count');

            JadwalSeminar.query({
                filter: 'tersedia',
                size: totalItems
            }, function(data2, headers) {
                vm.jadwalseminars = data2;
            });
        });

        Mahasiswa.get({id: vm.seminar.mahasiswaId}, function(mahasiswa) {
            vm.seminar.mahasiswa = mahasiswa;
        });
        
        function selectJadwalSeminar(jadwalSeminar) {
            vm.seminar.jadwalSeminarId = jadwalSeminar.id;

            Sesi.get({id: jadwalSeminar.sesiId}, function(data) {
                vm.seminar.jamMulai = data.jamMulai;
                vm.seminar.jamSelesai = data.jamSelesai;
            })
        }

        function save () {
            vm.isSaving = true;
            
            Seminar.update(vm.seminar, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('semhasApp:seminarUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
