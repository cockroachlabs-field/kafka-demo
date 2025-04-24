select job_id
from [show jobs]
where job_type = 'CHANGEFEED'
  and status = 'running';

cancel jobs (select job_id
             from [show jobs]
             where job_type = 'CHANGEFEED'
               and status = 'running');
