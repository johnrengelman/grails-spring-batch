package grails.plugins.springbatch.ui

import grails.test.mixin.TestFor

import org.junit.Before
import org.junit.Test

@TestFor(SpringBatchJobController)
class SpringBatchJobControllerUnitTests {

    def springBatchUiServiceMock

    @Before
    void setUp() {
        springBatchUiServiceMock = mockFor(SpringBatchUiService)
    }

    @Test
    void testList() {
        springBatchUiServiceMock.demand.getJobUiModel(1..1) {Map params ->
            assert 0 == params.offset
            assert 10 == params.max
            return [modelInstances: [], modelTotal: 0]
        }
        controller.springBatchUiService = springBatchUiServiceMock.createMock()

        def results = controller.list()

        assert results.modelInstances == []
        assert results.modelTotal == 0

        assert params.max == 10
        assert params.offset == 0
        springBatchUiServiceMock.verify()

    }

    @Test
    void testListWithParams() {
        springBatchUiServiceMock.demand.getJobUiModel(1..1) {Map params ->
            assert 10 == params.offset
            assert 5 == params.max
            return [modelInstances: [], modelTotal: 0]
        }
        controller.springBatchUiService = springBatchUiServiceMock.createMock()

        params.max = 5
        params.offset = 10
        def results = controller.list()

        assert results.modelInstances == []
        assert results.modelTotal == 0

        assert params.max == 5
        assert params.offset == 10
        springBatchUiServiceMock.verify()
    }
}
