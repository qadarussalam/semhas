'use strict';

describe('Controller Tests', function() {

    describe('Mahasiswa Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMahasiswa, MockJurusan, MockSeminar, MockPesertaSeminar;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMahasiswa = jasmine.createSpy('MockMahasiswa');
            MockJurusan = jasmine.createSpy('MockJurusan');
            MockSeminar = jasmine.createSpy('MockSeminar');
            MockPesertaSeminar = jasmine.createSpy('MockPesertaSeminar');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Mahasiswa': MockMahasiswa,
                'Jurusan': MockJurusan,
                'Seminar': MockSeminar,
                'PesertaSeminar': MockPesertaSeminar
            };
            createController = function() {
                $injector.get('$controller')("MahasiswaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'semhasApp:mahasiswaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
