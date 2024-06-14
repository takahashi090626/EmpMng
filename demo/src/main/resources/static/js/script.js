function sortRows() {
    const table = document.querySelector("table");
    const records = [];
    for (let i = 1; i < table.rows.length; i++) {
      const record = {};
      record.row = table.rows[i];
      record.key = table.rows[i].cells[this.cellIndex].textContent;
      records.push(record);
    }
    if (this.classList.contains('sort-asc')) {
      records.sort(compareKeysReverse);
      purgeSortMarker();
      this.classList.add('sort-desc');
    } else {
      records.sort(compareKeys);
      purgeSortMarker();
      this.classList.add('sort-asc');
    }
    for (let i = 0; i < records.length; i++) {
      table.appendChild(records[i].row);
    }
  }

  function purgeSortMarker() {
    document.querySelectorAll('th').forEach(th => {
      th.classList.remove('sort-asc');
      th.classList.remove('sort-desc');
    });
  }

  function compareKeys(a, b) {
    if (a.key < b.key) return -1;
    if (a.key > b.key) return 1;
    return 0;
  }

  function compareKeysReverse(a, b) {
    if (a.key < b.key) return 1;
    if (a.key > b.key) return -1;
    return 0;
  }