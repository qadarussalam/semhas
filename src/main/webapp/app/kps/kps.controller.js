(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('KpsController', KpsController);

    KpsController.$inject = ['$state', 'PesertaSeminar', 'Seminar', 'Mahasiswa'];

    function KpsController($state, PesertaSeminar, Seminar, Mahasiswa) {
        var vm = this;

        // TODO fix parameters
        Seminar.query({
            status: '',
            dosenId: ''
        }, function(data) {
            vm.seminars = data;

            angular.forEach(data, function(seminar) {
                Mahasiswa.get({id: seminar.mahasiswaId}, function(mhs) {
                    seminar.mahasiswa = mhs;
                });
            })
        });
    }
})();
