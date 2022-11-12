package nextstep.subway.application;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.dto.LineEditRequest;
import nextstep.subway.dto.LineRequest;
import nextstep.subway.dto.LineResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LineService {
    private final LineRepository lineRepository;

    public LineService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }


    public LineResponse saveLine(LineRequest lineRequest) {
        Line line = lineRepository.save(lineRequest.toLine());
        return LineResponse.of(line);
    }

    @Transactional(readOnly = true)
    public LineResponse findLine(Long id) {
        return LineResponse.of(lineRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("지하철 노선을 찾을 수 없습니다.")
                ));
    }

    @Transactional(readOnly = true)
    public List<LineResponse> findAllLines() {
        return lineRepository.findAll().stream()
                .map(line -> LineResponse.of(line))
                .collect(Collectors.toList());
    }

    public void deleteLine(Long id) {
        lineRepository.deleteById(id);
    }

    public void updateLine(Long id, LineEditRequest lineEditRequest) {
        Line line = lineRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("노선 정보를 조회할 수 없습니다.")
                );

        line.editLine(lineEditRequest);
    }
}