'use strict';

describe('Controller Tests', function() {

    describe('PesertaSeminar Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPesertaSeminar, MockMahasiswa, MockSeminar;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPesertaSeminar = jasmine.createSpy('MockPesertaSeminar');
            MockMahasiswa = jasmine.createSpy('MockMahasiswa');
            MockSeminar = jasmine.createSpy('MockSeminar');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PesertaSeminar': MockPesertaSeminar,
                'Mahasiswa': MockMahasiswa,
                'Seminar': MockSeminar
            };
            createController = function() {
                $injector.get('$controller')("PesertaSeminarDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'semhasApp:pesertaSeminarUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
